(defproject dragon_curve "1.0.0-SNAPSHOT"
  :description "Draw a dragon curve"

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.520" :scope "provided"]
                 [reagent "0.8.1"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.17"]]

  :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.18"]
                                  [cider/piggieback "0.4.0"]]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}}

  :cljsbuild {:builds {:app {:source-paths ["src"]
                             :compiler {:preamble ["reagent/react.js"]
                                        :output-to "resources/public/js/app.js"
                                        :output-dir "resources/public/js/app"
                                        :optimizations :advanced}}}})
