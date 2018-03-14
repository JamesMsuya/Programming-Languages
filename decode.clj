; *********************************************
; * gme
; *********************************************

;; utility functions
(load-file "include.clj")                                   ;; "c2i and "i2c"


(use 'clojure.java.io)
(require '[clojure.string :as str])
(use '[clojure.string :only [index-of]])


(defn read-as-list
	"Reads a file containing any arrangement of words and returns a list of words (each word is in turn a list of characters)."
	[filename]
	(let [doc (doall (slurp filename))]
		(for [word (str/split doc #"\s+")] (seq word))
		)
	)



;; -----------------------------------------------------
;; HELPERS

(defn str-value [word]
	(let [sum 0] (for [letter word] (+ sum (c2i letter)))

							 )
	)


(defn word-decoder
	"decodes a single word by using the mapping provided."
	[word permutation]

	;(println (index-of (str/join (str/split (str permutation) #"\s+")) "("))

	(let [name (for [letter word]

							 (i2c (- (index-of (str/join (str/split (str permutation)#"\s+")) letter) 1)))] name)

	)

(defn encoder
	"Encodes a single word using the random mapping provided"
	[word permutation]

	(let [name (for [letter word]
							 (nth permutation (c2i letter)))] name)

	)

(defn permutations
	"Generates all possible mapping in english alfabet"
	[colls]
	(if (= 1 (count colls))
		(list colls)
		(for [head colls
					tail (permutations (disj (set colls) head))]
			(cons head tail)))
	)

(defn word-permutation
	"This is a wrapper function for recursive permutation function"
	[]
	(let [word (for [num (reverse (range 0 26))] (i2c num))]
		(permutations word))
	)


(defn gen-encoder
	"This function encodes the whole document using a random mapping"
	[paragraph]
	(let [en-list (nth (word-permutation) (rand-int 5))]
		(for [word paragraph]
			(encoder word en-list)))

	)

(defn my-reader
	"Reads a file containing any arrangement of words and returns a list of words (each word is in turn a list of characters)."
	[filename]

	(let [doc (slurp filename)]
		(for [word (str/split doc #"\s+")] (seq word))
		)
	)

(defn spell-checker-0
	[word]
	;(println (str (seq (read-as-list "dictionary2.txt"))) (str (seq word)))
	(let [list (for [item (read-as-list "dictionary2.txt")] (seq item))] true))



(defn spell-checker-1
	[word]
	(def uppercount (dec (count (read-as-list "dictionary2.txt"))))
	(loop [lower 0
				 upper uppercount]
		(if (> lower upper) false
												(let [mid (quot (+ lower upper) 2)
															midvalue (nth (read-as-list "dictionary2.txt") mid)]
													(cond
														(> (reduce + (str-value midvalue)) (reduce + (str-value word))) (recur lower (dec mid))
														(< (reduce + (str-value midvalue)) (reduce + (str-value word))) (recur (inc mid) upper)
														(= midvalue word) true))))

	)

;; -----------------------------------------------------
;; DECODE FUNCTIONS

(defn Gen-Decoder-A
	[paragraph]
	(defn fx [paragraph] (let [list (word-permutation)]
												 (for [word paragraph]
														 (loop [num 0]
															 (if (= true (spell-checker-0 (word-decoder word (nth list num))))
																 (word-decoder word (nth list num))
																 (do
																	 ;(println "sa" num (str/join "" (str/split (str (nth list num)) #"\s+")) (word-decoder word (nth list num)) (spell-checker-0 (word-decoder word (nth list num))))
																	 (recur (inc num))
																	 )
																 )
															 )
														 )
													 )

		)
	)

(defn Gen-Decoder-B-0
	[paragraph]
	)

(defn Gen-Decoder-B-1
	[paragraph]

	)

(defn Code-Breaker
	[document decoder]
	(decoder document)
	)

;; -----------------------------------------------------
;; Test code...

(defn test_on_test_data
	[]
	(let [doc (read-as-list "document1.txt")]
		(println doc)

		)
	)


;; test code...
(test_on_test_data)
(println (spell-checker-0 "fuel"))

;;encodes the contents of document by using random mapping
(println "encoded contents of document1\n"(seq (gen-encoder (my-reader "document1.txt"))))

(println "\nencoded contents of document2\n"(seq (gen-encoder (my-reader "document2.txt"))))

;;decodes a single word using the given mapping. Its a helper function to help slve the whole document
(println "\nsgzs decodeds to " (word-decoder "sgzs" "(zabcdefghijklmnopqrstuvwxy)"))

;;this prints the output of of a code breaker function
(println (Code-Breaker (gen-encoder (my-reader "document1.txt")) (Gen-Decoder-A (gen-encoder (my-reader "document2.txt")))))


