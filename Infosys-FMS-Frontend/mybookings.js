document.addEventListener('DOMContentLoaded', () => {
    const bookingList = document.getElementById('bookingList');
    const jwtToken = localStorage.getItem('Token');

    // Fetch bookings
    fetch('http://localhost:8080/viewbookings', {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${jwtToken}`
        }
    })
    .then(response => response.json())
    .then(data => {
        
        displayBookings(data);
    })
    .catch(error => {
        console.error('Error fetching bookings:', error);
        alert('Error fetching bookings. Please try again.');
    });

    // Display bookings
    function displayBookings(bookings) {
        bookings.forEach(booking => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${booking.id}</td>
                <td>${new Date(booking.bookingDate).toLocaleString()}</td>
                <td>
                    <button class="show-details" data-id="${booking.id}">Show Details</button>
                    <button class="delete" data-id="${booking.id}">Delete</button>
                </td>
            `;
            bookingList.appendChild(tr);
        });

        document.querySelectorAll('.show-details').forEach(button => {
            button.addEventListener('click', (event) => {
                const bookingId = event.target.dataset.id;
                window.location.href = `bookingdetails.html?bookingId=${bookingId}`;
            });
        });

        document.querySelectorAll('.delete').forEach(button => {
            button.addEventListener('click', (event) => {
                const bookingId = event.target.dataset.id;
                deleteBooking(bookingId);
            });
        });
    }

    // Delete booking
    function deleteBooking(bookingId) {
        fetch(`http://localhost:8080/deletebooking/${bookingId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        })
        .then(response => {
            if (response.ok) {
                alert('Booking deleted successfully.');
                window.location.reload();  // Refresh the page to reflect the changes
            } else {
                alert('Error deleting booking. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error deleting booking:', error);
            alert('Error deleting booking. Please try again.');
        });
    }
});
