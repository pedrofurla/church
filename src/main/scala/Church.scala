/**
 * User: pedrofurla
 * Date: 11/09/13
 * Time: 08:17
 */
object Church {
  /* NUMBERS
  0 ≡ λf.λx. x
  1 ≡ λf.λx. f x
  2 ≡ λf.λx. f (f x)
  3 ≡ λf.λx. f (f (f x))
  4 ≡ λf.λx. f (f (f (f x)))

  succ ≡ λn.λf.λx. f (n f x)

  succ 1 ≡ λf.λx. f ( (λf.λx. f x) f x) ≡ λf.λx. f (f x) )

  plus ≡ λm.λn.λf.λx. m f (n f x)
  */
  
  
  /* BOOLEANS
  true ≡ λa.λb. a
  false ≡ λa.λb. b

  and ≡ λm.λn. m n m
  or ≡ λm.λn. m m n
  not1[1] ≡ λm.λa.λb. m b a
  not2[2] ≡ λm. m (λa.λb. b) (λa.λb. a)
  xor ≡ λa.λb. a (not2 b) b
  if ≡ λm.λa.λb. m a b

  and true false ≡ (λm.λn. m n m) (λa.λb. a) (λa.λb. b) ≡ (λa.λb. a) (λa.λb. b) (λa.λb. a) ≡ (λa.λb. b) ≡ false
  and true true  ≡ (λm.λn. m n m) (λa.λb. a) (λa.λb. a) ≡ (λa.λb. a) (λa.λb. a) (λa.λb. a) ≡ (λa.λb. a) ≡ false
   */

  
  /* PAIRS
  pair ≡ λx.λy.λz.z x y
  fst ≡ λp.p (λx.λy.x)
  snd ≡ λp.p (λx.λy.y)
   */


  // ----------
  type NUM[A] = (A => A) => A => A
  def succ  [A]:  NUM[A]  =>  NUM[A] = m => n => x => n(m(n)(x))
  def plus  [A]: (NUM[A]) => (NUM[A]) => NUM[A] = m => n => f => x => m(f)(n(f)(x))
  def times [A]: (NUM[A]) => (NUM[A]) => NUM[A] = m => n => f => x => m(n(f))(x)
  def isZero[A]: (NUM[A]) => BOOL[A] = m => a => b => m(_ => F(a)(b))(T(a)(b))
  //
  /*
  pred ≡ λn.λf.λx. n (λg.λh. h (g f)) (λu. x) (λu. u)
  SHIT: http://okmij.org/ftp/Computation/lambda-calc.html#predecessor
  http://citeseerx.ist.psu.edu/viewdoc/download;jsessionid=70E6927502DB038ABB1DF6AD0390312F?doi=10.1.1.26.7908&rep=rep1&type=pdf
  page 6
  */
  def pred  [A]: (NUM[A]) => NUM[A] = {
    n => f => x =>
    val ble = (g:A => A) => (h:A=>A) => (f:A=>A) => h(g(f(x)))
    n( z => ble((u:A) => z)((u:A) => u)(f) )(x)

  }

  def liftPair[A]: (A => A) =>(PAIR[A] => PAIR[A]) =
        f => ( p=> pair(snd(p))(f(snd(p))) )

  def numPair0[A]: NUM[A] => PAIR[NUM[A]] = m => pair(m)(m)
  def numPair1[A]: (NUM[A]) => (NUM[A]) => PAIR[NUM[A]] = m => n => pair(m)(n)
  def numPair2[A]: NUM[A] => (NUM[PAIR[A]]) = f => x => {
    val sp: PAIR[NUM[A]] => PAIR[NUM[A]] = p => pair(snd(p))(succ(snd(p)))
    val ps: (NUM[PAIR[A]]) => NUM[PAIR[A]] = n => f => x => n( p => pair(snd(p))(snd(p)) )(x)

    val paired = liftPair(f)

    def three [A]: NUM[A] = f => x => f(f(f(x)))

    ???
  }
  def numPair3[A]: NUM[A] => PAIR[NUM[A]] = m => f => x => ???

  def fstt[A](t:(A,A)) = t._1
  def sndt[A](t:(A,A)) = t._2


  assert( three[(Int,Int)]{ case (m,n) => (n, n+1) }((0,0)) == (2,3) )

  assert ( both( three[PAIR[Int]]{ p => pair(snd(p))(snd(p)+1) }(pair(0)(0)) ) == (2,3) )

  // ----------
  type BOOL[A] = A => A => A
  def T[A]:BOOL[A] = a => b => a
  def F[A]:BOOL[A] = a => b => b

  def test[A]: (BOOL[A]) => BOOL[A] = m => a => b => m(a)(b)
  def not [A]: (BOOL[A]) => BOOL[A] = m => a => b => m(b)(a)

  def and    [A]: (BOOL[A]) => (BOOL[A]) => BOOL[A] = m => n => a => b => m(n(a)(b))(m(a)(b))
  def or     [A]: (BOOL[A]) => (BOOL[A]) => BOOL[A] = m => n => a => b => m(m(a)(b))(n(a)(b))
  def implies[A]: (BOOL[A]) => (BOOL[A]) => BOOL[A] = m => n => or(not(m))(n)

  // ----------
  type PAIR[A] = (A=>A=>A)=>A
  def pair[A]: A=>A=>PAIR[A] = x => y => z => z(x)(y)
  def fst [A]: (PAIR[A]) => A = z => z(T[A])
  def snd [A]: (PAIR[A]) => A = z => z(F[A])
  
  // ----------
  def zero  [A]: NUM[A] = f => x => x
  def one   [A]: NUM[A] = f => x => f(x)
  def two   [A]: NUM[A] = f => x => f(f(x))
  def three [A]: NUM[A] = f => x => f(f(f(x)))
  
  def four  [A] = succ[A](three)
  def five  [A] = plus[A](four)(one)
  def six   [A] = plus[A](two)(four)
  def seven [A] = plus[A](one)(six)
  def eight [A] = times[A](two)(four)
  def nine  [A] = succ[A](eight)
  def ten   [A] = times[A](five)(two)

  def nvalues[A] = List(
    zero[A], one[A], two[A],
    three[A], four[A], five[A],
    six[A], seven[A], eight[A],
    nine[A],ten[A])

  // ----------
  def bvalues[A] = List(T[A],F[A])
  def binaryTable[A] = (for{ l <- bvalues[A]; r <- bvalues[A] } yield (l,r))

  // ----------
  val inc: Int => Int  = _ + 1
  def num: (NUM[Int]) => Int = n => n(inc)(0)
  def numStr: (NUM[String]) => String = n => n("f " + _ )("0")

  // ----------
  def bool: (BOOL[Boolean]) => Boolean = a =>  a(true)(false)

  def both[A]: PAIR[A] => (A,A) = p => (fst(p), snd(p))

  def main(x:Array[String]) = {

    println("---NUMBERS---")
    println(nvalues map num)

    println("---isZero---")
    println(nvalues[Boolean] map { isZero } map { bool })

    println("---NOT---")
    println(bvalues[Boolean] map { x => (bool(x) , bool(not(x))) } mkString("NOT: ", ",", "."))

    println("---AND---")
    println(binaryTable[Boolean] map { case (x,y) => ( bool(x) + " AND " + bool(y) , bool(and(x)(y))) } mkString("AND:\n", ",\n", "."))

    println("---OR---")
    println(binaryTable[Boolean] map { case (x,y) => ( bool(x) + " OR " +bool(y) , bool(or(x)(y))) } mkString("OR:\n", ",\n", "."))

    println("---IMPLIES---")
    println(binaryTable[Boolean] map { case (x,y) => ( bool(x) + " IMPLIES " +bool(y) , bool(implies(x)(y))) } mkString("IMPLIES:\n", ",\n", "."))

    println("---PAIRS---")
    val p1 = pair(1)(2)
    val p2 = pair(3)(4)

    println("p1:"+ ( fst(p1), snd(p1) ))
    println("p2:"+ ( fst(p2), snd(p2) ))

  }

}
