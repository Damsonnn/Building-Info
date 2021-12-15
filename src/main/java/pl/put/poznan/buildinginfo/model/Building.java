package pl.put.poznan.buildinginfo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.put.poznan.buildinginfo.logic.Localization;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Data Transfer Object class for building
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Building implements Localization {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("floors")
    private List<Floor> floors;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    /**
     * @return JSON file
     */

    @Override
    public String toString() {
        return "Building{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", floors=" + floors +
                '}';
    }

    /**
     * Method responsible for calculating surface  of a building
     * @return surface area as an integer
     */
    @Override
    public Integer calculateSurface() {
        return floors.stream()
                .map(Floor::calculateSurface)
                .reduce(0, Integer::sum);
    }

    /**
     * method responsible for calculating Volume of Building
     * @return integer value of sum of all Volume Floors
     */
    @Override
    public Integer calculateVolume() {
        return floors.stream()
                .map(Floor::calculateVolume)
                .reduce(0, Integer::sum);
    }

    /**
     * method responsible for calculating Heating of Floor
     * @return BigDecimal value of sum of all Heating Floors
     */
    @Override
    public BigDecimal calculateHeating() {
        return floors.stream()
                .map(Floor::calculateHeating)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    /**
     * method responsible for calculating Energy Consumption of floor per cube
     * @return BigDecimal value of Energy Consumption per volume unit in the building
     */
    @Override
    public BigDecimal calculateEnergy() {
        return this.calculateHeating().divide(BigDecimal.valueOf(this.calculateVolume()), 5, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateLight(){
        return floors.stream()
                .map(Floor::calculateLight)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }
}
