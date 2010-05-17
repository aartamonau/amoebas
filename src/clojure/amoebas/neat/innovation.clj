(ns amoebas.neat.innovation
  (:use [amoebas.utils.seq :only (enumerate)])
  (:import (amoebas.neat.link-gene link-gene)
           (amoebas.neat.neuron-gene neuron-gene)))

(def global-innovation-db (atom nil))

(defrecord neuron-innovation
  [id
   neuron-id
   neuron-in
   neuron-out])

(defn make-neuron-innovation
  ([id neuron]
     (make-neuron-innovation id nil nil (:id neuron)))
  ([id from to neuron-id]
     (new neuron-innovation
          id
          neuron-id
          (:id from)
          (:id to))))

(defrecord link-innovation
  [id
   neuron-in
   neuron-out])

(defn make-link-innovation
  ([id link]
     (make-link-innovation id (:neuron-in link) (:neuron-out link)))
  ([id in out]
     (new link-innovation
          id
          in
          out)))

(defrecord innovation-db
  [innovations
   next-neuron-id
   next-innovation-num])

(defn make-innovation-db-pure [links neurons]
  (let [neurons-count (count neurons)
        neuron-innovations
          (for [[i neuron] (enumerate neurons)]
            (make-neuron-innovation i neuron))
        links-count (count links)
        link-innovations
          (for [[i link] (enumerate links)]
            (make-link-innovation (+ i neurons-count) link))]
    (new innovation-db
         (vec (concat neuron-innovations
                                link-innovations))
         neurons-count
         (+ neurons-count links-count))))

(defn make-innovation-db [links neurons]
  (reset! global-innovation-db (make-innovation-db-pure links neurons)))

(defn dispatcher [db in out type]
  (if (and (= (class in) neuron-gene)
           (= (class out) neuron-gene))
    'wrapped
    'id))

(defmulti create-innovation-pure dispatcher)

(defmethod create-innovation-pure 'id
  [db in out type]
  (let [innovation
        (if (= type 'neuron)
          (make-neuron-innovation (:next-innovation-num db) in out
                                  (:next-neuron-id db))
          (make-link-innovation (:next-innovation-num db) in out))
        innovation-num (inc (:next-innovation-num db))
        neuron-id
        (if (= type 'neuron)
          (inc (:next-neuron-id db))
          (:next-neuron-id db))
        innovations (conj (:innovations db) innovation)]
    [(:next-innovation-num db)
     (assoc db :innovations innovations
               :next-innovation-num innovation-num
               :next-neuron-id neuron-id)]))

(defmethod create-innovation-pure 'wrapped
  [db in out type]
  (create-innovation-pure db (:id in) (:id out) type))

(defn create-innovation
  ([in out type]
     (let [[id db] (create-innovation-pure @global-innovation-db in out type)]
       (do (reset! global-innovation-db db)
           id))))


(defmulti find-innovation-pure dispatcher)

(defmethod find-innovation-pure 'wrapped
  [db in out type]
  (find-innovation-pure db (:id in) (:id out) type))

(defmethod find-innovation-pure 'id
  [db in out type]
  (let [type (if (= type 'neuron)
               neuron-gene
               link-gene)]
    (some #(and (= (:neuron-in %) in)
                (= (:neuron-out %)  out)
                (= (class %) type)
                (:id %))
          (:innovations db))))

(defn next-innovation-id-pure [db]
  (:next-innovation-num db))

(defn next-innovation-id []
  (next-innovation-id-pure @global-innovation-db))

(defn find-innovation [in out type]
  (find-innovation-pure @global-innovation-db in out type))

(defn find-or-create-innovation-pure [db in out type]
  (let [id (find-innovation-pure db in out type)]
    (if id
      [id db]
      (create-innovation-pure db in out type))))

(defn find-or-create-innovation [in out type]
  (let [[id db] (find-or-create-innovation-pure
                  @global-innovation-db in out type)]
    (do (reset! global-innovation-db db)
        id)))

(defn get-innovation-pure [db id]
  (nth (:innovations db) id))

(defn get-innovation [id]
  (get-innovation-pure @global-innovation-db))

(defn last-neuron-id-pure [db]
  (dec (:next-neuron-id db)))

(defn last-neuron-id []
  (last-neuron-id-pure @global-innovation-db))