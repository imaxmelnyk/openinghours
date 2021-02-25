# Opening Hours
Pretty print opening hours (wolt assignment)  

## Requirements
- `sbt` installed (tested on 1.4.7)  

## Test & Run
`sbt test` - for running tests  
`sbt run` - will run the server on http://localhost:8080  

## API
- `POST` to `/openinghours` with input json body  

## Example
`curl -X POST -H "Content-Type: application/json" -d 'example-input-json-body' http://localhost:8080/openinghours`

### Input
```
{
  "monday": [],
  "tuesday": [
    { "type": "open", "value": 36000 },
    { "type": "close", "value": 64800 }
  ],
  "wednesday": [],
  "thursday": [
    { "type": "open", "value": 36000 },
    { "type": "close", "value": 64800 }
  ],
  "friday": [
    { "type": "open", "value": 36000 }
  ],
  "saturday": [
    { "type": "close", "value": 3600 },
    { "type": "open", "value": 36000 }
  ],
  "sunday": [
    { "type": "close", "value": 3600 },
    { "type": "open", "value": 43200 },
    { "type": "close", "value": 75600 }
  ]
}
```

### Output
```
Monday: Closed
Tuesday: 10 AM - 6 PM
Wednesday: Closed
Thursday: 10 AM - 6 PM
Friday: 10 AM - 1 AM
Saturday: 10 AM - 1 AM
Sunday: 12 PM - 9 PM
```

## What to improve
Generally, the data format is good for such use case.  
I would, maybe, represent the whole week as a single timeline (ie from 0 to 604799 seconds) so that we dont deal with overnight opening hours and in case of monday-to-sunday opening hours just add additional seconds to the maximum (604799). I guess that would be easier to process.  
Another change is that i would use open timeshifts instead of open/close events. Again, easier to process.  
At the end, there will be an array of timeshifts for opening hours:
```
[{
    "open": 122400,
    "close": 151200
}, {
    "open": 295200,
    "close": 324000
}, {
    "open": 381600,
    "close": 435600
}, {
    "open": 468000,
    "close": 522000
}, {
    "open": 561600,
    "close": 594000
}]
```
