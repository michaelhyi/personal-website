describe("Navigation", () => {
   it("Navigation", () => {
      cy.visit("http://localhost:3000/");
      cy.get('a[href*="projects"]').click();
      cy.url().should("include", "/projects");
      cy.get("div").contains("Projects");
   });
});
