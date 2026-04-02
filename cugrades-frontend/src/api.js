const API_BASE = import.meta.env.VITE_API_BASE || '';

export function apiUrl(path) {
  return `${API_BASE}${path}`;
}

export async function fetchJson(path) {
  const response = await fetch(apiUrl(path));

  if (!response.ok) {
    throw new Error(`Request failed: ${response.status} ${response.statusText}`);
  }

  return response.json();
}
