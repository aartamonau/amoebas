(ns amoebas.neat.genome
  (:use amoebas.neat.neuron-gene
        amoebas.neat.link-gene
        amoebas.neat.innovation
        amoebas.neat.phenotype
        amoebas.utils.random
        amoebas.utils.seq))

(defrecord genome
  [id neurons links inputs-number outputs])

(defn make-genome
  ([id inputs outputs]
     (let [input-slice (/ 1 (+ inputs 2))
           output-slice (/ 1 (+ outputs 1))
           input-neurons
           (for [i (range inputs)]
             (make-neuron-gene 'input i (* (+ i 2) input-slice) 0))
           bias
           (make-neuron-gene 'bias inputs input-slice 0)
           output-neurons
           (for [i (range outputs)]
             (make-neuron-gene
              'output (+ i inputs 1) (* (+ i 1) output-slice) 1))
           neurons (vec (concat input-neurons [bias] output-neurons))
           links
           (for [i (range (+ inputs 1))
                 j (range outputs)]
             (make-link-gene
              (:id (nth neurons i))
              (:id (nth neurons (+ inputs j 1)))
              true
              (+ inputs outputs (* outputs i) j 1)
              (random -1 1)))]
       (do (make-innovation-db links neurons)
           (new genome
                id neurons links inputs outputs))))
  ([id neurons links inputs outputs]
     (do (assert (not (some nil? links)))
         (assert (not (some nil? neurons)))
         (new genome
              id neurons links inputs outputs))))

(defn duplicate-link? [genome from to]
  (some #(and (= (:from %) (:id from))
              (= (:to %) (:id to)))
        (:links genome)))

(defn find-neuron-by-id [genome id]
  (first (filter #(= (:id %) id) (:neurons genome))))

;; TODO: enabled/disabled links
(defn add-link [genome
                mutation-rate loop-chance
                loop-tries add-link-tries]
  (let [inputs (:inputs-number genome)
        neurons (count (:neurons genome))]
    (letfn [(try-find-loop-candidate
             []
             (if (< (random) loop-chance)
               (loop [i loop-tries]
                 (if (== i 0)
                   nil
                   (let [candidate-num (random-int (inc inputs) (dec neurons))
                         candidate (nth (:neurons genome) candidate-num)]
                     (if (or (:recurrent? candidate)
                             (= (:type candidate) 'bias)
                             (= (:type candidate) 'input))
                       (recur (dec i))
                       candidate-num))))
               nil))
            (try-find-link-candidates
             []
             (loop [i add-link-tries]
               (if (== i 0)
                 nil
                 (let [neuron-1-num (random-int 0 (dec neurons))
                       neuron-2-num (random-int (inc inputs) (dec neurons))
                       neuron-1 (nth (:neurons genome) neuron-1-num)
                       neuron-2 (nth (:neurons genome) neuron-2-num)]
                   (if (or (duplicate-link? genome neuron-1 neuron-2)
                           (= neuron-1 neuron-2))
                     (recur (dec i))
                     [neuron-1-num neuron-2-num (> (:split-y neuron-1)
                                                   (:split-y neuron-2))])))))
            (try-find-candidates
             []
             (let [candidate (try-find-loop-candidate)]
               (if candidate
                 ;; returning neurons to connect and a flag indicating whether
                 ;; a connection is recurrent
                 [candidate candidate true]
                 (let [[neuron-1-num neuron-2-num recurrent?]
                         (try-find-link-candidates)]
                   (if neuron-1-num
                     [neuron-1-num neuron-2-num recurrent?]
                     nil)))))
            (create-link
             [neuron-1-num neuron-2-num recurrent?]
             (let [neuron-1 (nth (:neurons genome) neuron-1-num)
                   neuron-2 (nth (:neurons genome) neuron-2-num)
                   id (find-or-create-innovation neuron-1 neuron-2 'link)
                   weight (random -1 1)
                   link (make-link-gene (:id neuron-1) (:id neuron-2)
                                        true id weight recurrent?)
                   links (conj (:links genome) link)]
               (do (assert (and (not (nil? neuron-1))
                                (not (nil? neuron-2))))
                   (assoc genome :links links))))]

      (if (< (random) mutation-rate)
        (let [[neuron-1-num neuron-2-num recurrent?] (try-find-candidates)]
          (if neuron-1-num
            (create-link neuron-1-num neuron-2-num recurrent?)
            genome))
        genome))))

(defn add-neuron [genome mutation-rate]
  (letfn [(find-link
           []
           (loop []
             (let [num   (random-int 0
                                     (dec (count (:links genome))))
                   link  (nth (:links genome) num)
                   in-id (:from link)
                   in    (find-neuron-by-id genome in-id)]
               (if (and (:enabled? link)
                        (not (:recurrent? link))
                        (not (= (:type in) 'bias)))
                 (do num)
                 (recur)))))
          (already-have-this-neuron-id?
           [id]
           (some #(= (:id %) id) (:neurons genome)))]
  (if (< (random) mutation-rate)
    (let [link-num (find-link)
          link     (assoc (nth (:links genome) link-num)
                     :enabled? false)
          weight   (:weight link)
          in       (find-neuron-by-id genome (:from link))
          out      (find-neuron-by-id genome (:to   link))
          depth    (/ (+ (:split-y in) (:split-y out)) 2)
          width    (/ (+ (:split-x in) (:split-x out)) 2)

          id       (find-innovation (:from link) (:to link) 'neuron)
          id       (when id
                     (let [innovation (get-innovation id)]
                       (if (already-have-this-neuron-id?
                            (:neuron-id innovation))
                         nil
                         id)))]
    (if id
      (let [neuron-id (:neuron-id (get-innovation id))
            id-link-1 (find-innovation (:from link) neuron-id 'link)
            id-link-2 (find-innovation neuron-id (:to link) 'link)]
        (do (assert (and id-link-1 id-link-2))
            (let [link-1 (make-link-gene (:from link)
                                         neuron-id
                                         true
                                         id-link-1
                                         1.0)
                  link-2 (make-link-gene neuron-id
                                         (:to link)
                                         true
                                         id-link-2
                                         weight)
                  neuron (make-neuron-gene 'hidden
                                           neuron-id
                                           width
                                           depth)]
              (assoc genome
                :links (conj (:links genome) link-1 link-2)
                :neurons (conj (:neurons genome) neuron)))))
      (let [id        (create-innovation (:from link) (:to link) 'neuron)
            neuron-id (last-neuron-id)
            neuron    (make-neuron-gene 'hidden neuron-id width depth)
            id-link-1 (create-innovation (:from link) neuron-id 'link)
            id-link-2 (create-innovation neuron-id (:to link) 'link)
            link-1    (make-link-gene (:from link) neuron-id true id-link-1 1.0)
            link-2    (make-link-gene neuron-id (:to link) true id-link-2 weight)]
        (assoc genome
          :links (conj (assoc (:links genome)
                         link-num link)
                       link-1 link-2)
          :neurons (conj (:neurons genome) neuron)))))

    genome)))

(defn mutate-weights [genome
                      mutation-rate
                      new-weight-probability
                      max-perturbation]
  (let [links
        (for [link (:links genome)]
          (if (< (random) mutation-rate)
            (if (< (random) new-weight-probability)
              (assoc link
                :weight (random -1 1))
              (assoc link
                :weight (+ (:weight link)
                           (random (- max-perturbation) max-perturbation))))

            link))]
    (assoc genome
      :links links)))

(defn mutate-activation-response [genome
                                  mutation-rate max-perturbation]
  (let [neurons
        (for [neuron (:neurons genome)]
          (if (< (random) mutation-rate)
            (assoc neuron
              :activation-response (+ (:activation-response neuron)
                                      (random (- max-perturbation)
                                              max-perturbation)))
            neuron))]
    (assoc genome
      :neurons neurons)))

(defn best-genome [mother mother-fitness
                   father father-fitness]
  (if (= mother-fitness father-fitness)
    (let [n-mother (count (:links mother))
          n-father (count (:links father))]
      (cond
       (< n-mother n-father) 'mother
       (> n-mother n-father) 'father
       :else (random-out-of 'mother 'father)))
    (if (> mother-fitness father-fitness)
      'mother
      'father)))

(defn crossover [mother mother-fitness
                 father father-fitness
                 child-id]
  (letfn [(neuron-from-id
           [genome id]
           (do (assert (not (nil? id)))
               (some #(and (= (:id %) id) %)
                     (:neurons genome))))]
    (let [best (best-genome mother mother-fitness father father-fitness)]
      (loop [[m & mrest :as mlinks] (:links mother)
             [f & frest :as flinks] (:links father)
             child-links   []
             child-neurons (sorted-set-by #(compare (:id %1) (:id %2)))]
        (cond
         (and (empty? mlinks) (empty? flinks))
           (make-genome child-id
                        (vec child-neurons) child-links
                        (:inputs-number mother) (:outputs-number mother))
         (and (empty? mlinks) (= best 'mother))
           (recur [] [] child-links child-neurons)
         (and (empty? flinks) (= best 'father))
           (recur [] [] child-links child-neurons)
         (and (not-empty mlinks)
              (not-empty flinks)
              (= (:innovation-id m) (:innovation-id f)))
           (let [[link genome]
                   (random-out-of [m mother] [f father])]
             (recur mrest frest
                    (conj child-links link)
                    (conj child-neurons
                          (neuron-from-id genome (:from link))
                          (neuron-from-id genome (:to link)))))
         :else
           (let [[link genome mlinks flinks]
                 (cond
                  (empty? mlinks) [f father [] frest]  ; best is 'father
                  (empty? flinks) [m mother mrest []]  ; best is 'mother
                  (< (:innovation-id m)
                     (:innovation-id f)) [m mother mrest flinks]
                  :else [f father mlinks frest])]      ; (> (:id m) (:id f))
                    (recur mlinks flinks
                           (conj child-links link)
                           (conj child-neurons
                                 (neuron-from-id genome (:from link))
                                 (neuron-from-id genome (:to link))))))))))

(defn genome-to-phenotype [genome]
  (let [neurons (map make-neuron (:neurons genome))
        ;; neurons  (apply sorted-map
        ;;                 (concat-map #(vector (:id %) (make-neuron %))
        ;;                             (:neurons genome)))
        outgoing-links (apply merge-with
                              concat
                              (map #(sorted-map (:from %) [(make-link %)])
                                   (:links genome)))
        ingoing-links  (apply merge-with
                              concat
                              (map #(sorted-map (:to %) [(make-link %)])
                                   (:links genome)))]

    (make-neural-net neurons ingoing-links outgoing-links)))
