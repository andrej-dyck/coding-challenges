import Data.List

main = print (find (== "haskell") (letterCombinations "4275355"))
-- output: Just "haskell"

letterCombinations :: String -> [String]
letterCombinations "" = []
letterCombinations d = (combinations . map digitLetters) d
    where combinations [] = [[]]
          combinations (x:xs) = [y:ys | y <- x, ys <- combinations xs]

digitLetters :: Char -> [Char]
digitLetters c = case c of
                 '2' -> "abc"
                 '3' -> "def"
                 '4' -> "ghi"
                 '5' -> "jkl"
                 '6' -> "mno"
                 '7' -> "pqrs"
                 '8' -> "tuv"
                 '9' -> "wxyz"
                 '0' -> " "
                 otherwise -> []