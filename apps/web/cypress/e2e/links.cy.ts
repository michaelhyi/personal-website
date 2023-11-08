import { contact } from "../../src/data/contact";
import { experience } from "../../src/data/experience";
import { projects } from "../../src/data/projects";

const links: string[] = [];

describe("links", () => {
  it("links", () => {
    contact.forEach((v) => {
      links.push(v.href);
    });

    projects.forEach((v) => {
      links.push(v.href);
    });

    experience.forEach((v) => {
      links.push(v.href);
    });

    links.push("https://michael-yi.com/Resume.pdf");

    test("http://localhost:3000/");
    test("http://localhost:3000/projects");
    test("http://localhost:3000/lauren");
    test("http://localhost:3000/1");
    test("http://localhost:3000/projects/1");
    test("http://localhost:3000/lauren/1");
  });
});

const test = (url: string) => {
  cy.visit(url);
  cy.get("a")
    .should("have.attr", "href")
    .then((href) => {
      expect(links).to.include(href);
    });
};
