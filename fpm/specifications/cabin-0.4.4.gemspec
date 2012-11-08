# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "cabin"
  s.version = "0.4.4"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Jordan Sissel"]
  s.date = "2012-03-23"
  s.description = "This is an experiment to try and make logging more flexible and more consumable. Plain text logs are bullshit, let's emit structured and contextual logs. Metrics, too!"
  s.email = ["jls@semicomplete.com"]
  s.executables = ["rubygems-cabin-test"]
  s.files = ["bin/rubygems-cabin-test"]
  s.homepage = "https://github.com/jordansissel/ruby-cabin"
  s.licenses = ["Apache License (2.0)"]
  s.require_paths = ["lib", "lib"]
  s.rubygems_version = "1.8.24"
  s.summary = "Experiments in structured and contextual logging"

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<json>, [">= 0"])
    else
      s.add_dependency(%q<json>, [">= 0"])
    end
  else
    s.add_dependency(%q<json>, [">= 0"])
  end
end
