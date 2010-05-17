(ns amoebas.neat.neuron-gene)

(defrecord neuron-gene
  [id type recurrent? activation-response split-x split-y])

(defn make-neuron-gene
  ([type id x y]
     (make-neuron-gene type id x y false))
  ([type id x y recurrent?]
     (new neuron-gene
          id type recurrent? 1 x y)))
