import java.io.File

fun main(args: Array<String>) {
    val test = "testInput.txt"
    val file = "d5p1input.txt"
    val lines = ArrayDeque<String>(object {}.javaClass.getResourceAsStream(file)?.bufferedReader()?.readLines() ?: throw IllegalArgumentException("File not found"))
    lines.removeIf {it == ""}

    //Get seed list
    val seeds = lines.removeFirst().substringAfter(':').trim().split(' ').map { it.toLong() }
    lines.removeFirst()

    //Seed to Soil maps
    var nextSection = lines.indexOf("soil-to-fertilizer map:") - 1
    val soilMaps = mutableListOf<plantMap> ()
    for(i in 0..nextSection) {
        soilMaps.add(plantMap(lines.removeFirst().split(' ').map { it.toLong() }))
    }
    lines.removeFirst()

    //Soil to Fertilizer maps
    nextSection = lines.indexOf("fertilizer-to-water map:") - 1
    val fertilizerMaps = mutableListOf<plantMap> ()
    for(i in 0..nextSection) {
        fertilizerMaps.add(plantMap(lines.removeFirst().split(' ').map { it.toLong() }))
    }
    lines.removeFirst()

    //Fertilizer to Water maps
    nextSection = lines.indexOf("water-to-light map:") - 1
    val waterMaps = mutableListOf<plantMap> ()
    for(i in 0..nextSection) {
        waterMaps.add(plantMap(lines.removeFirst().split(' ').map { it.toLong() }))
    }
    lines.removeFirst()

    //Water to Light maps
    nextSection = lines.indexOf("light-to-temperature map:") - 1
    val lightMaps = mutableListOf<plantMap> ()
    for(i in 0..nextSection) {
        lightMaps.add(plantMap(lines.removeFirst().split(' ').map { it.toLong() }))
    }
    lines.removeFirst()

    //Light to Temperature maps
    nextSection = lines.indexOf("temperature-to-humidity map:") - 1
    val temperatureMaps = mutableListOf<plantMap> ()
    for(i in 0..nextSection) {
        temperatureMaps.add(plantMap(lines.removeFirst().split(' ').map { it.toLong() }))
    }
    lines.removeFirst()

    //Temperature to Humidity maps
    nextSection = lines.indexOf("humidity-to-location map:") - 1
    val humidityMaps = mutableListOf<plantMap> ()
    for(i in 0..nextSection) {
        humidityMaps.add(plantMap(lines.removeFirst().split(' ').map { it.toLong() }))
    }
    lines.removeFirst()

    //Humidity to Location maps
    nextSection = lines.indexOf("humidity-to-location map:") - 1
    val locationMaps = mutableListOf<plantMap> ()
    for(i in 0..<lines.size) {
        locationMaps.add(plantMap(lines.removeFirst().split(' ').map { it.toLong() }))
    }

    //Final map operation
    val mappedSeeds = seeds.map { seed ->
        var lastSeed = seed
        var newSeed = seed
        for(soilMap in soilMaps) {
            newSeed = soilMap.map(newSeed)
            if(newSeed != lastSeed) {
                lastSeed = newSeed
                break
            }
        }
        for(fertilizerMap in fertilizerMaps) {
            newSeed = fertilizerMap.map(newSeed)
            if(newSeed != lastSeed) {
                lastSeed = newSeed
                break
            }
        }
        for(waterMap in waterMaps) {
            newSeed = waterMap.map(newSeed)
            if(newSeed != lastSeed) {
                lastSeed = newSeed
                break
            }
        }
        for(lightMap in lightMaps) {
            newSeed = lightMap.map(newSeed)
            if(newSeed != lastSeed) {
                lastSeed = newSeed
                break
            }
        }
        for(temperatureMap in temperatureMaps) {
            newSeed = temperatureMap.map(newSeed)
            if(newSeed != lastSeed) {
                lastSeed = newSeed
                break
            }
        }
        for(humidityMap in humidityMaps) {
            newSeed = humidityMap.map(newSeed)
            if(newSeed != lastSeed) {
                lastSeed = newSeed
                break
            }
        }
        for(locationMap in locationMaps) {
            newSeed = locationMap.map(newSeed)
            if(newSeed != lastSeed) {
                lastSeed = newSeed
                break
            }
        }
        return@map newSeed
    }

    mappedSeeds.forEach {println(it)}
    //println(mappedSeeds.min())
}

class plantMap constructor(args: List<Long>) {
    private val _sourceStart = args[1]
    private val _range = args[2]
    private val _destinationStart = args[0]
    private val _shift = _destinationStart - _sourceStart
    fun map(value: Long): Long {
        return if(value >= _sourceStart && value < _sourceStart + _range) value + _shift else value
    }
}