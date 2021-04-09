import Data.List

main = print (wordsAfter ("a", "good") "alice is a good girl she is a good student")
-- output: ["girl", "student"]

wordsAfter :: (String, String) -> String -> [String]
wordsAfter bigram text = [ w | (w1:w2:w:_) <- wordTriples text
                             , (w1,w2) == bigram ]
           where wordTriples = windowed 3 . words

windowed :: Int -> [a] -> [[a]]
windowed n = transpose . take n . tails