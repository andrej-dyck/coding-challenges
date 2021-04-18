import Data.List

main = print (truncateSentence 4 "Hello how are you Contestant")
-- output: "Hello how are you"

truncateSentence :: Int -> String -> String
truncateSentence k = unwords . take k . words