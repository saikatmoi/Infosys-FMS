document.addEventListener('DOMContentLoaded', () => {
    const urlParams = new URLSearchParams(window.location.search);
    const bookingId = urlParams.get('bookingId');
    const bookingDetails = document.getElementById('bookingDetails');
    const passengerTableBody = document.querySelector('#passengerTable tbody');
    const jwtToken = localStorage.getItem('Token');

    if (!bookingId) {
        bookingDetails.innerHTML = '<p>Invalid booking ID.</p>';
        return;
    }

    fetch(`http://localhost:8080/viewbooking/${bookingId}`, {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        console.log(data);
        displayBookingDetails(data);
        document.getElementById('downloadTicketBtn').addEventListener('click', () => downloadTicket(data));
    })
    .catch(error => {
        console.error('Error fetching booking details:', error);
        bookingDetails.innerHTML = '<p>Error fetching booking details. Please try again later.</p>';
    });

    function displayBookingDetails(booking) {
        const bookingDate = new Date(booking.bookingDate).toLocaleString();

        document.getElementById('userName').textContent = booking.user.userName;
        document.getElementById('userEmail').textContent = booking.user.email;
        document.getElementById('flightNumber').textContent = booking.scheduledFlight.flight.flightNumber;
        document.getElementById('carrierName').textContent = booking.scheduledFlight.flight.carrierName;
        document.getElementById('flightModel').textContent = booking.scheduledFlight.flight.model;
        document.getElementById('departureTime').textContent = new Date(booking.scheduledFlight.schedule.departureTime).toLocaleString();
        document.getElementById('arrivalTime').textContent = new Date(booking.scheduledFlight.schedule.arrivalTime).toLocaleString();
        document.getElementById('sourceAirport').textContent = booking.scheduledFlight.schedule.sourceAirport.airportName;
        document.getElementById('destinationAirport').textContent = booking.scheduledFlight.schedule.destinationAirport.airportName;

        bookingDetails.innerHTML = `
            <p><strong>Booking ID:</strong> ${booking.id}</p>
            <p><strong>Booking Date:</strong> ${bookingDate}</p>
            <p><strong>Ticket Cost:</strong> ${booking.ticketCost}</p>
        `;

        booking.passengerList.forEach(passenger => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${passenger.pnrNumber}</td>
                <td>${passenger.name}</td>
                <td>${passenger.age}</td>
                <td>${passenger.uin}</td>
            `;
            passengerTableBody.appendChild(row);
        });
    }

    function downloadTicket(booking) {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        const bookingDate = new Date(booking.bookingDate).toLocaleString();

        doc.setFontSize(20);
        doc.setTextColor(40, 113, 166);
        doc.text('Booking Ticket', 10, 20);

        doc.setFontSize(12);
        doc.setTextColor(0, 0, 0);
        doc.text(`Booking ID: ${booking.id}`, 10, 30);
        doc.text(`Booking Date: ${bookingDate}`, 10, 40);
        doc.text(`Ticket Cost: ${booking.ticketCost}`, 10, 50);
        doc.text(`Flight Number: ${booking.scheduledFlight.flight.flightNumber}`, 10, 60);
        doc.text(`Flight Carrier Name: ${booking.scheduledFlight.flight.carrierName}`, 10, 70);
        doc.text(`Flight Model: ${booking.scheduledFlight.flight.model}`, 10, 80);

        doc.setFontSize(16);
        doc.setTextColor(40, 113, 166);
        doc.text('Source Details', 10, 90);

        doc.setFontSize(12);
        doc.setTextColor(0, 0, 0);
        doc.text(`Departure: ${new Date(booking.scheduledFlight.schedule.departureTime).toLocaleString()}`, 10, 100);
        doc.text(`Arrival: ${new Date(booking.scheduledFlight.schedule.arrivalTime).toLocaleString()}`, 10, 110);
        doc.text(`Source Airport: ${booking.scheduledFlight.schedule.sourceAirport.airportName}`, 10, 120);

        doc.setFontSize(16);
        doc.setTextColor(40, 113, 166);
        doc.text('Destination Details', 10, 130);

        doc.setFontSize(12);
        doc.setTextColor(0, 0, 0);
        doc.text(`Destination Airport: ${booking.scheduledFlight.schedule.destinationAirport.airportName}`, 10, 140);

        doc.setFontSize(16);
        doc.setTextColor(40, 113, 166);
        doc.text('Passenger List', 10, 150);

        booking.passengerList.forEach((passenger, index) => {
            const yOffset = 160 + (index * 10);
            doc.setFontSize(12);
            doc.setTextColor(0, 0, 0);
            doc.text(`PNR Number: ${passenger.pnrNumber}, Name: ${passenger.name}, Age: ${passenger.age}, UIN: ${passenger.uin}`, 10, yOffset);
        });

        doc.save(`Booking_${booking.id}.pdf`);
    }
});
