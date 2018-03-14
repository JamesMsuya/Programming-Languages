; *********************************************
; *James Msuya
; *********************************************

(use 'clojure.java.io)
(require '[clojure.string :as string])


(def operations
  ["+" "(" ")" "*" "-" "/"])

(def keywords
  ["and" "or" "not" "while" "for" "equal" "append" "concat" "set" "deffun" "if" "then" "else" "false" "true"]
  )

(defn my-reader [filename]
  (let [doc (slurp filename)]
    (for [word (string/split doc #"\s+")] (seq word))
    )
  )

;checks if a string contains a number.Helper function
(defn numFinder [string]
  (if (not(empty? string))
    (loop [counter 0]
      (if (>= counter (count string))
        false
        (if (and (<= 48 (int (nth string counter))) (>= 57 (int (nth string counter))))
          true
          (recur (inc counter))
          )
      )
      )false
    )
  )

;checks if a string is a number or not
(defn isNumber [string]
  (if (not(empty? string))
    (loop [counter 0]
      (if (>= counter (count string))
        true
        (if (or (> 48 (int (nth string counter))) (< 57 (int (nth string counter))))
          false
          (recur (inc counter))
          )
      )
    )false
  )
)

;count occurance of a character in a list
(defn occurance [character string]
  (count (filter (fn [x] (= x character)) string ))
  )

;return a sublist of the specified character only
(defn my_filter [character string]
  (filter #(= % character) string )
  )

;personolized filter than gives a list without the specified character
(defn my_fil [character string]
    (let [word (filter #(not= % character) string)] (seq word)
    )
  )

;check if its a string and also its not a keyword
(defn isString [string]
  (if (= false (numFinder (string/join (my_fil \) (my_fil \( string)))))
        (if (>= (count(my_filter (string/join (my_fil \) (my_fil \( string))) keywords)) 1) false
                    (if (>= (count(my_filter (string/join (my_fil \) (my_fil \( string))) operations)) 1) false true)))

  )

;check for any mixted variable i.e variables containing numbers
(defn has_Number_String [doc]
  (let [counter (for [word doc] (if (isNumber (string/join(my_fil \)(my_fil \( word)))) false (numFinder (string/join(my_fil \)(my_fil \( word))))) )]
    (my_filter true counter)
    )
  )


(defn wordAnalyser [word]
  (let [worda (if (= \( (nth word 0)) "OPERATOR : LEFT_PAR '(', " "")
           wordb (if (>= (count(my_filter (string/join (my_fil \) (my_fil \( word))) keywords)) 1) (str "Keyword : " (string/join (my_fil \) (my_fil\( word))) ", ") "")
                wordc (if (>= (count(my_filter (string/join (my_fil \) (my_fil \( word))) operations)) 1) (str "Operator : "  (string/join (my_fil \) (my_fil\( word))) ", ") "")
                      wordd (if (isNumber (string/join (my_fil \) (my_fil \( word)))) (str "IntegerVal : " (string/join (my_fil \) (my_fil\( word))) ", " ) "")
                          worde (if (and (not(empty? (string/join (my_fil \) (my_fil\( word))))) (isString word)) (str "Id : " (string/join (my_fil \) (my_fil\( word))) ", " ) "")
                               wordf (for [item word] (if (= \) item) ' "OPERATOR : RIGHT_PAR ')', " "" ))] (str worda wordb wordc wordd worde (string/join wordf))
           )
  )


(defn lexer
  "Takes a file name as a parameter"
  [filename]
    (let [doc (my-reader filename)]
        (if (not= (occurance \( (flatten doc)) (occurance \) (flatten doc))) "Syntax error:Bracket don't match"
        ;else brackets do match
        (if (< 0 (count(has_Number_String doc)) ) "Syntax error: variables contains numbers" (for [lex doc] (wordAnalyser lex))))

    )
  )

(println (lexer "coffeeSample.coffee"))
