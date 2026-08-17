document.addEventListener('DOMContentLoaded', () => {
  const priceInput = document.getElementById('item-price');
  const addTaxPriceSpan = document.getElementById('add-tax-price');
  const profitSpan = document.getElementById('profit');

  priceInput.addEventListener('input', () => {
    
    const priceValue = parseInt(priceInput.value, 10);

    if (!isNaN(priceValue) && priceValue > 0) {
      const taxPrice = Math.floor(priceValue * 0.1); 
      const profit = priceValue - taxPrice;            

      addTaxPriceSpan.textContent = taxPrice.toLocaleString();
      profitSpan.textContent = profit.toLocaleString();
    } else {
      addTaxPriceSpan.textContent = '';
      profitSpan.textContent = '';
    }
  });
});