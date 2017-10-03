module Main where

import Prelude hiding (Bool, and)

type Bool a = a -> a -> a

true2 ::  Bool a
true2 a b = a

false2 ::  Bool a
false2 a b = b
    
--and2 :: Bool a -> Bool a -> Bool a
and2 l r a b = l (r a b) (l a b)

or2 l r a b  = l (l a b) (r a b)


main :: IO ()
main = do
  putStrLn "hello world"
