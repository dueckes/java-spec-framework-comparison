require 'rubygems'
require 'bundler'

ENV["BUNDLE_GEMFILE"] = File.dirname(__FILE__) + '/../resources/Gemfile'

Bundler.require(:default, :test)

require 'java'
