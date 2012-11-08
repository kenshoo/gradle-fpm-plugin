# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "clamp"
  s.version = "0.3.1"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Mike Williams"]
  s.date = "2012-04-03"
  s.description = "Clamp provides an object-model for command-line utilities.  \nIt handles parsing of command-line options, and generation of usage help.\n"
  s.email = "mdub@dogbiscuit.org"
  s.homepage = "http://github.com/mdub/clamp"
  s.require_paths = ["lib"]
  s.rubygems_version = "1.8.24"
  s.summary = "a minimal framework for command-line utilities"

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
    else
    end
  else
  end
end
