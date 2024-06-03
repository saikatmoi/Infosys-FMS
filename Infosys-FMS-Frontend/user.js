document.addEventListener('DOMContentLoaded', () => {
  const sourceAirportSelect = document.getElementById('sourceAirport');
  const destinationAirportSelect = document.getElementById('destinationAirport');
  const travelDateInput = document.getElementById('travelDate');
  const searchButton = document.getElementById('searchButton');
  const resultsDiv = document.getElementById('results');

  // Fetch and populate airports
  fetch('http://localhost:8080/airports')
      .then(response => response.json())
      .then(data => {
          populateAirportSelect(sourceAirportSelect, data);
          populateAirportSelect(destinationAirportSelect, data);
      });

  // Populate airport options
  function populateAirportSelect(selectElement, airports) {
      airports.forEach(airport => {
          const option = document.createElement('option');
          option.value = airport.airportCode;
          option.text = `${airport.airportName} (${airport.airportLocation})`;
          selectElement.appendChild(option);
      });
  }

  // Enable search button if all fields are filled
  [sourceAirportSelect, destinationAirportSelect, travelDateInput].forEach(element => {
      element.addEventListener('input', () => {
          if (sourceAirportSelect.value && destinationAirportSelect.value && travelDateInput.value) {
              searchButton.disabled = false;
          } else {
              searchButton.disabled = true;
          }
      });
  });

  // Search flights
  searchButton.addEventListener('click', () => {
      const srcAirportId = sourceAirportSelect.value;
      const desAirportId = destinationAirportSelect.value;
      const date = travelDateInput.value;

      const requestBody = {
          srcAirportId: srcAirportId,
          desAirportId: desAirportId,
          date: date
      };

      fetch('http://localhost:8080/getflightsbydate', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(requestBody)
      })
          .then(response => response.json())
          .then(data => {
            console.log(data);
              displayResults(data);
          });
  });

  // Display results
  function displayResults(flights) {
    resultsDiv.innerHTML = '';
    if (flights.length === 0) {
        resultsDiv.innerHTML = '<p>No flights found.</p>';
    } else {
        const ul = document.createElement('ul');
        flights.forEach(flight => {
            const li = document.createElement('li');
            const flightInfo = `
               <strong>Scheduled Flight Id:</strong> ${flight.id}<br>
                <strong>Flight Number:</strong> ${flight.flight.flightNumber}<br>
                <strong>Carrier:</strong> ${flight.flight.carrierName}<br>
                <strong>Model:</strong> ${flight.flight.model}<br>
                <strong>Departure Time:</strong> ${new Date(flight.departureTime).toLocaleString()}<br>
                <strong>Arrival Time:</strong> ${new Date(flight.arrivalTime).toLocaleString()}<br>
                <strong>Available Seats:</strong> ${flight.availableSeats}<br>
                <strong>Ticket Price:</strong> ${flight.ticketPrice} INR
                <a href="book.html?scheduledFlightId=${flight.id}" class="book-link">Book Now</a>
            `;
            li.innerHTML = flightInfo;
            ul.appendChild(li);
        });
        resultsDiv.appendChild(ul);
    }
}
});