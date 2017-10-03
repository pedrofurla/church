{-# LANGUAGE FlexibleContexts #-}

module Treeish where

-- This example requires the containers, 
-- diagrams-core, diagrams-lib, diagrams-contrib and diagrams-svg packages
import Data.Tree
import Diagrams.Prelude 
import Diagrams.TwoD.Layout.Tree -- (renderTree,symmLayout',_slHSep,_slVSep, slHSep, slVSep)
import Diagrams.Backend.SVG (SVG)
import Diagrams.Backend.SVG.CmdLine (defaultMain)

exampleTree :: Tree String
exampleTree = Node "A" [Node "B" [], Node "C" []]

renderNodeTree :: Tree String -> QDiagram SVG V2 Double Any
renderNodeTree nodeTree = renderTree 
    (\a -> letter a `atop` square 1.03 # fc white) 
    (~~) 
    (symmLayout' (with{ _slHSep = 3,  _slVSep = 2}) nodeTree)
  where
     letter a = text a # font "monospace" # fontSize (local 0.47) 

t1 :: Tree Char
t1 = Node 'A' [Node 'B' (map lf "CDE"), Node 'F' [Node 'G' (map lf "HIJKLM"), Node 'N' (map lf "OPQR")]]
  where lf x = Node x []

{- 
    renderTree :: 
      (Monoid' m, Floating n, Ord n) => 
        (a -> QDiagram b V2 n m) -> 
        (P2 n -> P2 n -> QDiagram b V2 n m) -> 
        Tree (a, P2 n) -> QDiagram b V2 n m
-}

exampleSymmTree :: QDiagram SVG V2 Double Any
exampleSymmTree = 
  renderTree ((<> circle 1 # fc white) . text . (:[]))
             (~~)
             (symmLayout' (with & slHSep .~ 4 & slVSep .~ 4) t1)
  # centerXY # pad 1.1

main :: IO ()
main = defaultMain  exampleSymmTree -- (renderNodeTree exampleTree)