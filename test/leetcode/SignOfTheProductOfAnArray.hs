import Data.List

main = print (
         map signOfArray [
              [-1,-2,-3,-4,3,2,1]
            , [1,5,0,2,-3]
            , [-1,1,-1,1,-1]
         ]
    )
-- output: [1, 0, -1]

signOfArray :: [Int] -> Int
signOfArray = product . map signum