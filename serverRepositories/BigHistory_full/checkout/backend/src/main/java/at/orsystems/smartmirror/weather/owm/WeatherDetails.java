package at.orsystems.smartmirror.weather.owm;

import at.orsystems.smartmirror.weather.IconManager;
import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains more information about the weather and weather condition codes.
 * Represents the weather JSON object from openweathermap.
 *
 * @author Michael Ratzenböck
 * @since 2020
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "main", "description", "icon"})
public class WeatherDetails {
    /**
     * Weather condition id
     */
    public final Long id;
    /**
     * Group of weather parameters (Rain, Snow, Extreme,...)
     */
    public final String weatherSummary;
    /**
     * Weather condition within the group internationalized.
     */
    public final String weatherDescription;
    /**
     * Contains the path to the icon.
     */
    public final String iconPath;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonCreator
    public WeatherDetails(@JsonProperty(value = "id") Long id,
                          @JsonProperty(value = "weatherSummary", required = true) @JsonAlias("main") String weatherSummary,
                          @JsonProperty(value = "weatherDescription", required = true) @JsonAlias("description") String weatherDescription,
                          @JsonProperty(value = "iconPath", required = true) @JsonAlias("icon") String iconId) {
        this.id = id;
        this.weatherSummary = weatherSummary;
        this.weatherDescription = weatherDescription;
        this.iconPath = iconId2IconPath(iconId);
    }

    private String iconId2IconPath(String iconId) {
        return IconManager.instance()
                .getPathForId(iconId);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id)
                .append("main", weatherSummary)
                .append("weatherDescription", weatherDescription)
                .append("icon", iconPath)
                .append("additionalProperties", additionalProperties)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(iconPath)
                .append(weatherDescription)
                .append(weatherSummary)
                .append(id)
                .append(additionalProperties)
                .toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof WeatherDetails rhs)) {
            return false;
        }
        return new EqualsBuilder().append(iconPath, rhs.iconPath)
                .append(weatherDescription, rhs.weatherDescription)
                .append(weatherSummary, rhs.weatherSummary)
                .append(id, rhs.id)
                .append(additionalProperties, rhs.additionalProperties)
                .isEquals();
    }

}
