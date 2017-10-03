{-# LANGUAGE NoImplicitPrelude #-} 

module Grammar.DSL where

--import qualified Prelude as P
import Prelude(String, ($), (==))

import Grammar

-- Constructors

v :: String -> Term
v = Var

λ :: String -> Term -> Term
λ = Lam 

(^), (←), (*-), (◦) :: Term -> Term -> Term
(^)= App
(←) = App
(*-) = App 
-- f, g :: Lam
f ◦ g =  Lam "a" $ App f $ App g $ v "a" 

infixl 5 ^
infixl 5 ← 
infixl 5 *-
infixl 5 ◦


-- combinators

id, compose, const, true, false :: Term

-- | Simple combinators
--
-- Examples:
--
-- >>> id == Lam "a" (Var "a")
-- True
--
-- prop> id == Lam "a" (Var "a")
-- 
-- prop>  (==) const $ Lam "a" $ Lam "b" $ Var "a" 
--
-- 
id = λ "a" $ v "a"
compose = λ "f" $ λ "g" $ λ "a" $ App (App (v "f") (v "g")) $ v "a"
const = λ "a" $ λ "b" $ v "a"
true = const
false = λ "a" $ λ "b" $ v "b" 

