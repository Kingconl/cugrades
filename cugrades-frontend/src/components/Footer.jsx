
export default function Footer() {
  return (
    <footer className="site-footer">
      <div className="container site-footer-content">
        <p>
          Unofficial student-made project. Not affiliated with, endorsed by, or sponsored by Carleton University.
        </p>
        <p>
          Grade data for Fall 2024 through Winter 2025 was obtained through a Freedom of Information and Protection of Privacy Act request.
        </p>
        <p>
          <a
            href="https://www.google.com/search?q=N%2FA"
            target="_blank"
            rel="noreferrer"
            className="footer-link"
          >
            GitHub
          </a>{' '}
          • Maintained and developed by Connor Liu
        </p>
      </div>
    </footer>
  );
}
