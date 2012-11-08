HEADER_MAGIC = "\x8e\xad\xe8\x01\x00\x00\x00\x00"
TAG_ENTRY_SIZE = 16 # tag id, type, offset, count == 16 bytes

def read_header(io)
  # RPM 'header' section looks like:
  #
  # MAGIC (8 bytes) index_count (4 bytes), data_length (4 bytes )
  #
  # * index_count is the number of 'tags' in this header.
  # * data_length is a blob containing all the values for the tags
  #
  # Header has a header of 'magic' + index_count (4 bytes) + data_length (4 bytes)
  #p "start of header" => io.pos
  data = io.read(16).unpack("a8NN")

  # TODO(sissel): @index_count is really a count, rename?
  @magic, @index_count, @data_length = data
  if @magic != HEADER_MAGIC
    puts "Magic value in header was wrong. Expected #{HEADER_MAGIC.inspect}, but got #{@magic.inspect}"
    exit 1
  end
  
  @index_size = @index_count * TAG_ENTRY_SIZE
  tag_data = io.read(@index_size)
  data = io.read(@data_length)
  #p "end of header" => io.pos

  (0 ... @index_count).each do |i|
    offset = i * TAG_ENTRY_SIZE
    entry_data = tag_data[i * TAG_ENTRY_SIZE, TAG_ENTRY_SIZE]
    tag, tag_type, offset, count  = entry_data.unpack("NNNN")
    if block_given?
      yield :tag => tag, :type => tag_type, :offset => offset, :count => count
    end
    #entry << data
  end # each index
  return 16 + @index_size + @data_length
end

if ARGV.length != 1
  puts "Usage: #{$0} blah.rpm"
  exit 1
end

rpm = File.new(ARGV[0])

# Read the 'lead' - it's mostly an ignored part of the rpm file.
lead = rpm.read(96)
magic, major, minor, type, archnum, name, osnum, signature_type, reserved = lead.unpack("A4CCnnA66nnA16")

puts "RPM file version #{major}.#{minor} (#{signature_type == 5 ? "signed" : "unsigned"})"

if signature_type == 5
  # Read a header for the rpm signature. This has the same format as a normal
  # rpm header
  puts "Checking signature"
  length = read_header(rpm) do |tag|
    # print each tag in this header
    p tag
  end

  # signature headers are padded up to an 8-byte boundar, details here:
  # http://rpm.org/gitweb?p=rpm.git;a=blob;f=lib/signature.c;h=63e59c00f255a538e48cbc8b0cf3b9bd4a4dbd56;hb=HEAD#l204
  # Throw away the pad.
  rpm.read((length % 8))
end

# Read the rpm header
puts "Checking header"
read_header(rpm)
p rpm.read(4)
