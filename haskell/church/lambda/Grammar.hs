{-# LANGUAGE NoImplicitPrelude #-}

module Grammar where

import qualified Prelude as P
import Prelude(String)

data Term  = 
    Var String
  | Lam String Term
  | App Term Term
  deriving (P.Show, P.Eq)

-- data Term a = 
--  --   Val a 
--     Var String
--   | Lam Var (Term a)
--   | App (Term a) (Term a)

-- type TermI = Term Int

-- -- valI :: Int -> Term Int
-- -- valI i = Val i 

-- varI :: Int -> Term Int
-- varI s = Var s

-- lamI :: String -> Term Int -> Term Int
-- lamI s t = Lam (Var s) t

-- app :: Term
-- app = 


