(ns count-files.core
  "Core package of the count-files utility."
  (:require [me.raynes.fs :as fs]
            [clojure.string :as str :only (replace-first)]
            [clojure.tools.cli :as cmdline :only (cli)])
  (:gen-class :main true))

(defn find-files
  "Returns a sequence of all files found within a directory tree."
  [path]
  (fs/find-files* path fs/file?))

(defn file-components 
  "Return a map containing the extension, name, size and path of a given file."
  [f]
  { :extension (fs/extension f),
    :name (.getName f),
    :size (.length f),
    :path (.getParent f) })

(defn count-files-by
  "Returns a map with a count of all files found within a directory tree,
  grouped by one of the file components (or any function of its components).
  The grouping function is given a map containing the extension, name, size
  and path of each file (aka the file components), and should calculate and
  return a grouping key. Sample usage: `(count-files-by :extension path)`"
  [grouping-fn path]
  (let [files (find-files path)
        components (map file-components files)]
    (frequencies (map grouping-fn components))))

(defn children-dirs
  "Returns a sequence of all children directories of a given directory"
  [path]
  (filter fs/directory? (.listFiles path)))

(defn count-files
  "Count the files of the base directory given on the command line
  (as the first argument). If the --walk switch was specified, counts files
  within each child directory; otherwise count starts at the base directory."
  [opts args]

  (let [basedir  (first args)
        basedir* (-> basedir fs/file fs/normalized-path)]
    (println (str "Counting files per extension within directory tree " basedir*))

    (if (:walk opts)
      (doseq [d (children-dirs basedir*)]
        (println (str d "/: \t" (count-files-by :extension d))))
      (println (count-files-by :extension basedir*))
    )))

(defn print-help
  "Displays help and sample usage, preceeded by an optional message."
  ([banner]
    (print-help banner "Count all files of a directory tree, grouping them by their extension."))
  ([banner msg]
    (println (str msg "\n"
      (str/replace-first banner "Usage:" "Usage:\n\n count-files [switches] basedir")))))

(defn -main
  "Command-line interpreter and main entry point."
  [& args]

  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))

  (let [
    [opts args banner] (cmdline/cli args
      ["-h" "--help" "Show help" :flag true :default false]
      ["-w" "--walk" "Count files of children dirs (instead of specified dir)" :flag true :default false])
  ]
    (cond
      (:help opts) ((print-help banner) (System/exit 0))
      (empty? args) ((print-help banner "Base directory is a required argument.") (System/exit 1))
      :else (count-files opts args)
  ))
)