describe("404", () => {
  it("404", () => {
    cy.visit("http://localhost:3000/1");
    cy.get("div").contains("404, not found.");

    cy.visit("http://localhost:3000/lauren/1");
    cy.get("div").contains("404, not found.");

    cy.visit("http://localhost:3000/projects/1");
    cy.get("div").contains("404, not found.");
  });
});
