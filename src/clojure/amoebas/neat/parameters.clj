(ns amoebas.neat.parameters)

(def tours-per-generation 2)
(def elite-ratio 0.05)

(def winner-score 3)
(def draw-score 1)
(def loser-score 0)

;; add-link params
(def chance-to-add-link 0.07)
(def chance-to-add-recurrent-link 0.05)
(def tries-to-find-looped-link 5)
(def tries-to-add-link 5)

;; add-neuron params
(def chance-to-add-neuron 0.04)

;; mutate-weight params
(def mutation-rate 0.2)
(def probability-to-replace-weight 0.1)
(def max-weight-perturbation 0.5)

;; mutate-activation-response params
(def activation-mutation-rate 0.1)
(def max-activation-perturbation 0.1)