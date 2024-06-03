document.addEventListener('DOMContentLoaded', () => {
    const bookingForm = document.getElementById('bookingForm');
    const addPassengerButton = document.getElementById('addPassengerButton');
    const passengerListDiv = document.getElementById('passengerList');
    let passengerCount = 1;

    const urlParams = new URLSearchParams(window.location.search);
    const scheduledFlightId = urlParams.get('scheduledFlightId');
    document.getElementById('scheduledFlightId').value = scheduledFlightId;

    // Add another passenger form fields
    addPassengerButton.addEventListener('click', () => {
        passengerCount++;
        const passengerDiv = document.createElement('div');
        passengerDiv.classList.add('form-group');
        passengerDiv.innerHTML = `
            <label for="passengerName${passengerCount}">Passenger Name ${passengerCount}</label>
            <input type="text" id="passengerName${passengerCount}" required>
            <label for="passengerAge${passengerCount}">Passenger Age ${passengerCount}</label>
            <input type="number" id="passengerAge${passengerCount}" required>
            <label for="passengerUIN${passengerCount}">Passenger UIN ${passengerCount}</label>
            <input type="number" id="passengerUIN${passengerCount}" required>
        `;
        passengerListDiv.appendChild(passengerDiv);
    });

    // Handle form submission
    bookingForm.addEventListener('submit', (event) => {
        event.preventDefault();

        const passengers = [];
        for (let i = 1; i <= passengerCount; i++) {
            passengers.push({
                name: document.getElementById(`passengerName${i}`).value,
                age: parseInt(document.getElementById(`passengerAge${i}`).value),
                uin: parseInt(document.getElementById(`passengerUIN${i}`).value)
            });
        }

        const bookingData = {
            scheduledFlightId: parseInt(scheduledFlightId),
            passengerList: passengers
        };
        console.log(bookingData);

        const jwtToken = localStorage.getItem('Token');

        fetch('http://localhost:8080/booktickets', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`
            },
            body: JSON.stringify(bookingData)
        })
        .then()
        .then(data => {
            alert('Booking successful!');
            window.location.href = 'mybookings.html';  // Redirect to a confirmation page (implement this page as needed)
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Booking failed. Please try again.');
        });
    });
});
