describe("Routes", () => {
  it("routes", () => {
    cy.visit("http://localhost:3000/");
    cy.url().should("include", "/");
    cy.get("div").contains("Michael Yi");

    cy.visit("http://localhost:3000/lauren");
    cy.url().should("include", "/lauren");
    cy.get("div").contains("coming soon...");

    cy.visit("http://localhost:3000/projects");
    cy.url().should("include", "/projects");
    cy.get("div").contains("Projects");
  });
});
