(defproject remind-me "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [reagent "0.5.1" :exclusions [cljsjs/react]]
                 [cljsjs/react-with-addons "0.13.3-0"]
                 [re-frame "0.6.0"]
                 [compojure "1.4.0"]
                 [ring "1.4.0"]
                 [org.xerial/sqlite-jdbc "3.7.2"]
                 [com.layerware/hugsql "0.4.4"]
                 [cljs-ajax "0.5.3"]
                 [ring/ring-json "0.4.0"]
                 [org.clojure/data.json "0.2.6"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :main remind-me.core

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs      ["resources/public/css"]
             :ring-handler  remind-me.handler/app}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "remind-me.core/mount-root"}
                        :compiler {:main remind-me.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main remind-me.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false}}]})
