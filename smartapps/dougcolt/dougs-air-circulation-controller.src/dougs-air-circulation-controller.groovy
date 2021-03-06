/**
 *  AIR CIRCULATION CONTROLLER
 *
 *  Copyright 2018 Doug Colt
 */
 
definition(
    name: "DOUG'S AIR CIRCULATION CONTROLLER",
    namespace: "dougcolt",
    author: "Doug Colt",
    description: "Uses a dummy switch to turn on manual air circulation on furnace ",
    category: "My Apps",
    iconUrl: "https://cdn.device-icons.smartthings.com/Entertainment/entertainment15-icn@2x.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {	
     section("Select the Thermostat:") {
        input "theThermostat", "capability.thermostatMode", required: true
    }

	section("Select the Air Circulation Virtual Switch:") {
        input "theSwitch", "capability.switch", required: true
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(theSwitch, "switch.on", onDetectedHandler)
	subscribe(theSwitch, "switch.off", offDetectedHandler)
	subscribe(theThermostat, "thermostatFanMode.auto", fanAutoDetectedHandler)
	subscribe(theThermostat, "thermostatFanMode.on", fanOnDetectedHandler)
}  

def onDetectedHandler(evt) {
    log.debug "onDetectedHandler called: $evt"
    theThermostat.fanOn()
}

def offDetectedHandler(evt) {
	log.debug "offDetectedHandler called: $evt"
	theThermostat.fanAuto()
}

def fanOnDetectedHandler(evt) {
    log.debug "fanOnDetectedHandler called: $evt"
	theSwitch.on()
}

def fanAutoDetectedHandler(evt) {
    log.debug "fanAutoDetectedHandler called: $evt"
	theSwitch.off()
}
     
  
     
     
