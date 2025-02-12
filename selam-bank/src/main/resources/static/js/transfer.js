document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const fromSelect = document.getElementById('fromAccountId');
    const toSelect = document.getElementById('toAccountId');

    form.addEventListener('submit', function(e) {
        if (fromSelect.value === toSelect.value) {
            e.preventDefault();
            alert('Cannot transfer to the same account');
        }
    });

    fromSelect.addEventListener('change', function() {
        Array.from(toSelect.options).forEach(option => {
            option.disabled = option.value === fromSelect.value;
        });
    });
}); 