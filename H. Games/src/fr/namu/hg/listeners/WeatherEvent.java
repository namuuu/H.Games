package fr.namu.hg.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherEvent {
	
	@EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
     
        boolean rain = event.toWeatherState();
        if(rain)
            event.setCancelled(true);
    }
 
    @EventHandler
    public void onThunderChange(ThunderChangeEvent event) {
     
        boolean storm = event.toThunderState();
        if(storm)
            event.setCancelled(true);
    }
}
