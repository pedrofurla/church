-- module Main where

import           Test.DocTest

main :: IO ()
main = do
  let args = ["-isrc", "lambda/Grammar/DSL.hs"]
  putStrLn $ "\n** Tests on: " ++ show args ++ "\n"
  doctest args