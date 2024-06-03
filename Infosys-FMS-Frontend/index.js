document.addEventListener('DOMContentLoaded', () => {
  const registrationForm = document.getElementById('registrationForm');
  const responseMessage = document.getElementById('responseMessage');

  registrationForm.addEventListener('submit', async (e) => {
      e.preventDefault();

      const formData = new FormData(registrationForm);
      const user = {
          userName: formData.get('userName'),
          password: formData.get('password'),
          email: formData.get('email'),
          userPhone: parseInt(formData.get('userPhone')),
          role: 'ROLE_USER'
      };

      try {
          const response = await fetch('http://localhost:8080/registration', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json'
              },
              body: JSON.stringify(user)
          });

          if (response.ok) {
              responseMessage.textContent = 'Success ! Check Email for Verification ';
              responseMessage.style.color = 'green';
          } else {
              const errorData = await response.json();
              responseMessage.textContent = `${errorData.info}`;
              responseMessage.style.color = 'red';
          }
      } catch (error) {
          responseMessage.textContent = 'An error occurred. Please try again.';
          responseMessage.style.color = 'red';
      }
  });
});
