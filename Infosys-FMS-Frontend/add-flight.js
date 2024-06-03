document.addEventListener('DOMContentLoaded', () => {
    const addFlightForm = document.getElementById('addFlightForm');
    const responseMessage = document.getElementById('responseMessage');
    const jwtToken = localStorage.getItem('Token');

    addFlightForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = new FormData(addFlightForm);
        const flight = {
            seatCapacity: parseInt(formData.get('seatCapacity')),
            carrierName: formData.get('carrierName'),
            model: formData.get('model')
        };

        try {
            const response = await fetch('http://localhost:8080/addflight', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${jwtToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(flight)
            });

            if (response.ok) {
                responseMessage.textContent = 'Flight added successfully!';
                responseMessage.style.color = 'green';
            } else {
                const errorData = await response.json();
                responseMessage.textContent = `Error: ${errorData.message}`;
                responseMessage.style.color = 'red';
            }
        } catch (error) {
            responseMessage.textContent = 'An error occurred. Please try again.';
            responseMessage.style.color = 'red';
        }
    });
});
