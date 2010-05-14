(ns amoebas.neat.neuron-gene)

(defstruct neuron-gene
  :id
  :type
  :recurrent?
  :activation-response
  :split-x
  :split-y)

(defn make-neuron-gene
  ([type id x y]
     (make-neuron-gene type id x y false))
  ([type id x y recurrent?]
     (struct-map neuron-gene
       :id id
       :type type
       :recurrent? recurrent?
       :activation-response 1
       :split-x x
       :split-y y)))
