import axios from "axios";

export const fetchCountries = async () => {
  const response = await axios.get("https://restcountries.com/v3.1/all");
  return response.data
    .map(country => ({
      value: country.cca3, 
      label: country.name.common 
    }))
    .sort((a, b) => a.label.localeCompare(b.label)); 
};