import scala.language.implicitConversions

/**
 * Created by pedrofurlanetto on 10/4/15.
 */


sealed trait Term
object Term {
  import scalaz.Equal
  import scalaz.Show
  import scalaz.syntax.show._

  val L: (Symbol, Term) => Term = (s, t) => Lambda.apply(s.name, t)
  val A: (Term, Term) => Term = Apply.apply
  val V: String       => Var = Var.apply

  implicit val TermEqual:Equal[Term] = Equal.equal( _ == _)
  val TermShow:Show[Term] = Show.shows[Term]{
    case Var(s) => s
    case Apply(x, y) => x.shows + " " + y.shows
    case Lambda(s, t) => "\\" + s + "." + t.shows
  }

  implicit val TermShow2:Show[Term] = Show.shows[Term]{
    case Var(s) => s
    case Apply(Apply(x,y), Apply(a,b)) => " (" + A(x,y).shows + ") (" + A(a,b).shows + ") "
    case Apply(l@Lambda(v,t), Apply(a,b)) => " (" + (l:Term).shows + ") (" + A(a,b).shows + ") "
    case Apply(l@Lambda(v,t), y) => " (" + (l:Term).shows + ") " + y.shows
    case Apply(x, Apply(a,b)) => x.shows + " (" + A(a,b).shows+") "
    case Apply(x, y) => x.shows + " " + y.shows
    case Lambda(s, t) => "\\" + s + "." + t.shows
  }
}
case class Var(n:String) extends Term
case class Apply(l:Term, r:Term) extends Term
case class Lambda(v:String, t:Term) extends Term

object SampleTerms {

  import Term._

  val (x,y,z) = (V("x"), V("y"),V("z"))
  val (a,b,c) = (V("a"), V("b"),V("c"))
  val (f,m,n) = (V("f"), V("m"),V("n"))

  val id = L('x, x)

  val self = L('x, A(x, x))
  val self2 = A(self, self)

  val pair = L('a, L('b, L('f, A(A(f, a), b ) ))) // \a b f. f a b

  val zero = L('f, L('z, z))
  val succ = L('n, L('f, L('z, // \n\f\z. f (n f) z
    A(f, A( A(n, f), z ))
  )))

  val succA = A(succ, _:Term)

  val churchNumbers = Stream.iterate(zero)(succA)

  val _::one::two::three::Nil = churchNumbers.take(4).toList

  val pairxy = A(A(pair,x), y) // (pair x y)

  val add = L('x, L('y, L('f, L('z, // \x\y\f\z. x f (y f) z
    A(
      A(x, f),
      A(A(y, f),z)
    )
  ))))

  val mult = L('m, L('n, L('f,
    A(m, A(n, f))
  ) ) )


  val I = L('x, x)           // I := \x.x
  val K = L('x, L('y, x))    // K := \x.\y.x
  val S = L('x, L('y, L('z,  // S := \x.\y.\z.xz(yz)
    A( A(x, z), A(y, z))
  )))

}

object LambdaCalc {

  import Term._

  def eval(t: Term): Term = t match {
    case v @ Var(_) => v              // t := x
    case Apply(l, r) => l match {     // t := M N
      case Lambda(v, t) =>            // t := (\x.M) N replace t[v := r]
        beta(v, t, r)
      case Var(_) => Apply(l, eval(r))      // t := x N
      case Apply(_, _) => Apply(eval(l), r)         // t := x y N
    }
     // apply(l,r)
    case Lambda(v, t) => Lambda(v,eval(t))
  }

  import scalaz.syntax.equal._
  import scalaz.syntax.show._

  import SampleTerms._

  println(
    List(
      eval(x) === x,
      eval(id) === id,
      eval(Apply(id, z)) === z,
      eval(self2) === self2
    )
  )

  /** Beta reduction */
  def beta(v: String, t: Term, bound:Term):Term  = t match { // t[v := bound], replace v for bound in t
    case Var(`v`) =>
      bound
    case v2 @ Var(_) =>
      v2
    case Apply(l, r)  =>
      Apply(beta(v, l, bound), beta(v, r, bound))
    case l @ Lambda(`v`, t2) => // new binding for the same "identifier", no need to go deeper
      l
    case Lambda(v2, t2) =>
      Lambda(v2, beta(v, t2, bound))
  }
  def beta(v:Symbol, t:Term, bound:Term):Term = beta(v.name, t, bound)

  println(
  List(
    beta('x, x, y) === y,
    beta('x, z, y) === z,
    beta('x, A(x,x), z) === A(z,z),
    beta('x, L('x, A(x,x)), z) === L('x, A(x,x)),
    beta('x, L('x, y), z) === L('x, y),
    beta('x, L('y, x), z) === L('y, z)
  )  )


  println(List(I,K,S) map { _.shows } mkString "\n")

  def normalNaive(t:Term) =
    Stream.iterate( (eval(t),t) ){
      case (t1,t0) => (eval(t1), t1)
    }.takeWhile{ t => t._1 != t._2 }

  def reduced(t: Term) = normalNaive(t).last._1

  /*val nats = //Stream.fill(5)(A(succ,_:Term)).scanRight(zero)(_ apply _)
    Stream.iterate(zero)(succA) map reduced
*/
  def steps(t:Term) = t.shows #:: normalNaive(t).map(_._1.shows)

}
