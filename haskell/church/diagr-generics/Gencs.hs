{-# LANGUAGE DeriveGeneric, DefaultSignatures  #-}


module Main where

import GHC.Generics
import Generics.Deriving.ConNames(conNameOf)

-- data Prod = Prod Int String deriving Generic
-- data Prod' a b = Prod' a b deriving Generic

-- data Sum = L Int | R String deriving Generic
-- data Sum' l r = L' l | R' r deriving Generic

data Simple a = Sim a  deriving Generic 
-- newtype Simple' a = Simple' a  deriving (Generic)

data One = Onee deriving (Generic)

--instance ConNames Simple

-- instance Constructor One

-- data Empty deriving (Generic)

--name = conName $ from $ One

main :: IO ()
main = do
  let l1 = conNameOf Onee
  putStrLn l1
  putStrLn $ datatypeName $ from Onee
  -- putStrLn $ conName $ (from :: Simple' Int)



{-

==================== Derived instances ====================
Derived instances:
  instance GHC.Generics.Generic (Main.Simple a_a1yf) where
    GHC.Generics.from x_a1yg
      = GHC.Generics.M1
          (case x_a1yg of {
             Main.Sim g1_a1yh
               -> GHC.Generics.M1 (GHC.Generics.M1 (GHC.Generics.K1 g1_a1yh)) })
    GHC.Generics.to (GHC.Generics.M1 x_a1yi)
      = case x_a1yi of {
          GHC.Generics.M1 (GHC.Generics.M1 (GHC.Generics.K1 g1_a1yj))
            -> Main.Sim g1_a1yj }
  
  instance GHC.Generics.Generic Main.One where
    GHC.Generics.from x_a1yk
      = GHC.Generics.M1
          (case x_a1yk of { Main.Onee -> GHC.Generics.M1 GHC.Generics.U1 })
    GHC.Generics.to (GHC.Generics.M1 x_a1yl)
      = case x_a1yl of { GHC.Generics.M1 GHC.Generics.U1 -> Main.Onee }
  

GHC.Generics representation types:
  type GHC.Generics.Rep (Main.Simple a_a1ye) = GHC.Generics.D1
                                                 ('GHC.Generics.MetaData
                                                    "Simple" "Main" "main" 'GHC.Types.False)
                                                 (GHC.Generics.C1
                                                    ('GHC.Generics.MetaCons
                                                       "Sim" 'GHC.Generics.PrefixI 'GHC.Types.False)
                                                    (GHC.Generics.S1
                                                       ('GHC.Generics.MetaSel
                                                          'GHC.Base.Nothing
                                                          'GHC.Generics.NoSourceUnpackedness
                                                          'GHC.Generics.NoSourceStrictness
                                                          'GHC.Generics.DecidedLazy)
                                                       (GHC.Generics.Rec0 a_a1ye)))
  type GHC.Generics.Rep Main.One = GHC.Generics.D1
                                     ('GHC.Generics.MetaData "One" "Main" "main" 'GHC.Types.False)
                                     (GHC.Generics.C1
                                        ('GHC.Generics.MetaCons
                                           "Onee" 'GHC.Generics.PrefixI 'GHC.Types.False)
                                        GHC.Generics.U1)



-}