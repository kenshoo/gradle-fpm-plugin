# -*- encoding: utf-8 -*-

Gem::Specification.new do |s|
  s.name = "fpm"
  s.version = "0.4.20"

  s.required_rubygems_version = Gem::Requirement.new(">= 0") if s.respond_to? :required_rubygems_version=
  s.authors = ["Jordan Sissel"]
  s.date = "2012-10-05"
  s.description = "Convert directories, rpms, python eggs, rubygems, and more to rpms, debs, solaris packages and more. Win at package management without wasting pointless hours debugging bad rpm specs!"
  s.email = "jls@semicomplete.com"
  s.executables = ["fpm", "fpm-npm"]
  s.files = ["bin/fpm", "bin/fpm-npm"]
  s.homepage = "https://github.com/jordansissel/fpm"
  s.require_paths = ["lib", "lib"]
  s.rubygems_version = "1.8.24"
  s.summary = "fpm - package building and mangling"

  if s.respond_to? :specification_version then
    s.specification_version = 3

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<json>, ["= 1.6.6"])
      s.add_runtime_dependency(%q<cabin>, ["~> 0.4.3"])
      s.add_runtime_dependency(%q<backports>, ["= 2.6.2"])
      s.add_runtime_dependency(%q<arr-pm>, ["~> 0.0.7"])
      s.add_runtime_dependency(%q<clamp>, ["= 0.3.1"])
      s.add_development_dependency(%q<rush>, [">= 0"])
      s.add_development_dependency(%q<rspec>, [">= 0"])
      s.add_development_dependency(%q<insist>, ["~> 0.0.5"])
    else
      s.add_dependency(%q<json>, ["= 1.6.6"])
      s.add_dependency(%q<cabin>, ["~> 0.4.3"])
      s.add_dependency(%q<backports>, ["= 2.6.2"])
      s.add_dependency(%q<arr-pm>, ["~> 0.0.7"])
      s.add_dependency(%q<clamp>, ["= 0.3.1"])
      s.add_dependency(%q<rush>, [">= 0"])
      s.add_dependency(%q<rspec>, [">= 0"])
      s.add_dependency(%q<insist>, ["~> 0.0.5"])
    end
  else
    s.add_dependency(%q<json>, ["= 1.6.6"])
    s.add_dependency(%q<cabin>, ["~> 0.4.3"])
    s.add_dependency(%q<backports>, ["= 2.6.2"])
    s.add_dependency(%q<arr-pm>, ["~> 0.0.7"])
    s.add_dependency(%q<clamp>, ["= 0.3.1"])
    s.add_dependency(%q<rush>, [">= 0"])
    s.add_dependency(%q<rspec>, [">= 0"])
    s.add_dependency(%q<insist>, ["~> 0.0.5"])
  end
end
