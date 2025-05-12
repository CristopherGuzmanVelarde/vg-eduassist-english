document.addEventListener('DOMContentLoaded', function () {
    const gradeForm = document.getElementById('gradeForm');
    const gradesTableBody = document.getElementById('gradesTable').getElementsByTagName('tbody')[0];

    // Cargar calificaciones existentes al iniciar
    loadGrades();

    gradeForm.addEventListener('submit', function (event) {
        event.preventDefault();

        const formData = new FormData(gradeForm);
        const gradeData = {
            studentId: formData.get('studentId'),
            courseId: formData.get('courseId'),
            gradeValue: parseFloat(formData.get('gradeValue')),
            gradeDate: formData.get('gradeDate')
        };

        // Asumiendo que el endpoint de creación es /grades y es un POST
        fetch('/grade-management', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(gradeData),
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => { throw err; });
            }
            return response.json();
        })
        .then(data => {
            console.log('Calificación guardada:', data);
            addGradeToTable(data); // Asumiendo que la respuesta es la calificación guardada
            gradeForm.reset();
        })
        .catch(error => {
            console.error('Error al guardar calificación:', error);
            alert('Error al guardar calificación: ' + (error.message || JSON.stringify(error)));
        });
    });

    function loadGrades() {
        // Asumiendo que el endpoint para obtener todas las calificaciones es /grades y es un GET
        fetch('/grades')
            .then(response => {
                if (!response.ok) {
                    return response.json().then(err => { throw err; });
                }
                return response.json();
            })
            .then(grades => {
                gradesTableBody.innerHTML = ''; // Limpiar tabla antes de cargar
                grades.forEach(grade => {
                    addGradeToTable(grade);
                });
            })
            .catch(error => {
                console.error('Error al cargar calificaciones:', error);
                // alert('Error al cargar calificaciones: ' + (error.message || JSON.stringify(error)));
            });
    }

    function addGradeToTable(grade) {
        const row = gradesTableBody.insertRow();
        row.insertCell(0).textContent = grade.studentId;
        row.insertCell(1).textContent = grade.courseId;
        row.insertCell(2).textContent = grade.gradeValue;
        row.insertCell(3).textContent = grade.gradeDate;
    }
});