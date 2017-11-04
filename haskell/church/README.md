# Church

An attempt in classic lambda calculus

# On testing

Tests done with [Doctest](https://github.com/sol/doctest#readme), allows running `$ doctest .../source.hs` 

A test can be for example:

```haskell
-- | Compute Fibonacci numbers
--
-- Examples:
--
-- >>> fib 10       -- doctest style
-- 55               -- this is the desired repl outpub
--
-- prop> fib 5 == 5 -- Quickcheck style
-- 
fib :: Int -> Int
fib 0 = 0
fib 1 = 1
fib n = fib (n - 1) + fib (n - 2)
```