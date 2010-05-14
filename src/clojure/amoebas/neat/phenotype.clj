(ns amoebas.neat.phenotype
  (:use amoebas.utils.seq))

(defstruct link
  :from
  :to
  :weight)

(defstruct neuron
  :id
  :type
  :activation-response)

(defstruct neuron-state
  :output)

(defstruct neural-net
  :neurons
  :inputs
  :outputs
  :in-links                             ; a map from neuron id to the links
                                        ; ingoing to the corresponding neuron
  :out-links                            ; as in-links but for outgoing links
  :state

  :outputs-ids)

(defn make-neuron [neuron-gene]
  (struct-map neuron
    :id (:id neuron-gene)
    :type (:type neuron-gene)
    :activation-response (:activation-response neuron-gene)))

(defn make-neuron-state
  ([] (make-neuron-state 0))
  ([output]
     (struct-map neuron-state
       :output output)))

(defn make-link [link-gene]
  (struct-map link
    :from   (:from link-gene)
    :to     (:to link-gene)
    :weight (:weight link-gene)))

(defn make-neural-net [neurons in-links out-links]
  (let [inputs-count  (count (filter #(= (:type %) 'input) neurons))
        outputs       (filter #(= (:type %) 'output) neurons)
        outputs-count (count outputs)
        state         (zipmap (map :id neurons) (repeat (make-neuron-state)))]
    (struct-map    neural-net
      :neurons     neurons
      :in-links    in-links
      :out-links   out-links
      :state       state
      :inputs      inputs-count
      :outputs     outputs-count
      :outputs-ids (map :id outputs))))

(defn sigmoid [input response]
  (/ 1
     (+ 1
        (. Math exp (/ (- input)
                       response)))))

(defn compute-neuron-input [neural-net neuron]
  (let [state      (:state neural-net)
        in-links   ((:in-links neural-net) (:id neuron))
        in-outputs (map #(* (:output (state (:from %)))
                            (:weight %))
                        in-links)]
    (reduce + in-outputs)))

(defn compute-neuron-output [neural-net neuron]
  (let [input (compute-neuron-input neural-net neuron)]
    (sigmoid input (:activation-response neuron))))

(defn process-single-neuron [neural-net neuron]
  (let [output       (compute-neuron-output neural-net neuron)
        state        (:state neural-net)
        neuron-state (state (:id neuron))
        neuron-state (assoc neuron-state
                       :output output)
        state        (assoc state
                       (:id neuron) neuron-state)]
    (assoc neural-net
      :state state)))

(defn update [neural-net inputs]
  (let [state   (:state neural-net)
        neurons (:neurons neural-net)

        inputs-state (apply merge
                            (map #(sorted-map (:id %1)
                                            (make-neuron-state %2))
                                 neurons inputs))
        bias         (nth neurons (:inputs neural-net))
        bias-state   (sorted-map (:id bias)
                                 (make-neuron-state 1))

        rest (drop (inc (:inputs neural-net)) neurons)

        neural-net (assoc neural-net
                     :state (merge state inputs-state bias-state))]
    (loop [nn neural-net
           [n & ns :as neurons] rest]
      (if (empty? neurons)
        nn
        (recur (process-single-neuron nn n) ns)))))

(defn outputs [neural-net]
  (map :output
       (vals (select-keys (:state neural-net)
                          (:outputs-ids neural-net)))))