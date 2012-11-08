$: << "lib"

require "rpm/file"

rpm = RPM::File.new(ARGV[0])

#p rpm.lead
rpm.signature.tags.each do |tag|
  #p :tag => [tag.tag, tag.type, tag.count, tag.value]
end

rpm.header.tags.each do |tag|
  #next unless tag.tag.to_s =~ /(payload|sig)/
#  p :tag => [tag.tag, tag.type, tag.count, tag.value]
end

require "awesome_print"
ap rpm.requires

#payload = rpm.payload
#fd = File.new("/tmp/rpm.payload", "w")
#fd.write(rpm.payload.read)
