# http://ruby.bastardsbook.com/chapters/io/
require "open-uri" 

@new_properties = []

def gen_properties(line, filename, key_prefix)
    begin
    %w{h1 p li}.each do |tag|
        if line =~ /<#{tag}>.*<\/#{tag}>/
            next if line =~ /.*\$\{.*/
            value = line[/<#{tag}>(.*)<\/#{tag}>/, 1]
            if @tag_map[tag]
                @tag_map[tag] = @tag_map[tag] + 1
            else
                @tag_map[tag] = 1
            end
            key = "#{key_prefix}#{filename.gsub(/^_/, '').gsub(/\.gsp/,'')}.#{tag}.#{@tag_map[tag]}"
            key = key.gsub(/\/workspaces\/kettler\/retail\/grails-app\/views\/verbiage\//,'')
                     .gsub(/ /,'.')
                     .gsub(/\//,'.')
                     .gsub(/_verbiage\./,'')
            @new_properties << "#{key}=#{value}"
            return line.gsub(/#{value.gsub(/\(/,'\(').gsub(/\)/,'\)').gsub(/\*\*/,'\*\*')}/, "<g:message code=\"#{key}\"/>")
        end
    end
    rescue
        puts filename
    end
    line
end
verbiages = [
#'/workspaces/kettler/retail/grails-app/views/verbiage/bikes/adult/_verbiage.gsp',
'/workspaces/kettler/retail/grails-app/views/verbiage/bikes/child carriers/_verbiage.gsp',
'/workspaces/kettler/retail/grails-app/views/verbiage/bikes/men/_verbiage.gsp',
'/workspaces/kettler/retail/grails-app/views/verbiage/bikes/unisex/_verbiage.gsp',
'/workspaces/kettler/retail/grails-app/views/verbiage/bikes/women/_verbiage.gsp'
]
verbiages.each_with_index do |filename, i|
    puts filename[/\/workspaces\/kettler\/retail-git\/grails\/views\/verbiage\/(.*)/, 1]
    gsp_file = File.open(filename[/\/workspaces\/kettler\/retail\/grails-app\/views\/verbiage\/(.*)/, 1], "w")
    lines = IO.readlines(filename)
    @tag_map = Hash.new
    lines.each do |line|
        gsp_file << gen_properties(line, filename, 'verbiage.')
    end
    gsp_file.close
end
prop_file = File.open("properties.txt", "w")
@new_properties.each {|l| prop_file << "#{l}\n"}
prop_file.close

#Dir.foreach("/workspaces/kettler/retail-git/grails-app/views/features") do |filename|
    #next if File.directory?(filename)
    #gsp_file = File.open(filename, "w")
    #lines = IO.readlines("/workspaces/kettler/retail-git/grails-app/views/features/"+filename)
    #@tag_map = Hash.new
    #lines.each do |line|
        #gsp_file << gen_properties(line, filename, 'features.item.')
    #end
    #gsp_file.close
#end
#prop_file = File.open("properties.txt", "w")
#@new_properties.each {|l| prop_file << "#{l}\n"}
#prop_file.close
