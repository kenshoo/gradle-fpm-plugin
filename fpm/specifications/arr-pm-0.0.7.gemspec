# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "arr-pm"
  s.version = "0.0.7"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Jordan Sissel"]
  s.date = "2012-03-16"
  s.description = "This library allows to you to read and write rpm packages. Written in pure ruby because librpm is not available on all systems"
  s.email = ["jls@semicomplete.com"]
  s.licenses = ["Apache 2"]
  s.require_paths = ["lib", "lib"]
  s.rubygems_version = "1.8.24"
  s.summary = "RPM reader and writer library"

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<cabin>, ["> 0"])
    else
      s.add_dependency(%q<cabin>, ["> 0"])
    end
  else
    s.add_dependency(%q<cabin>, ["> 0"])
  end
end
