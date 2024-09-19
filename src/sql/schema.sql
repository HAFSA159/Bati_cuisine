CREATE TABLE Project (
  id SERIAL PRIMARY KEY,
  projectName VARCHAR(255) NOT NULL,
  surface REAL NOT NULL,
  profitMargin REAL NOT NULL,
  totalCost REAL NOT NULL,
  projectStatus TEXT DEFAULT 'IN_PROGRESS' CHECK (projectStatus IN ('IN_PROGRESS', 'COMPLETED', 'CANCELLED')) NOT NULL
);

CREATE TABLE Component (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  componentType TEXT CHECK (componentType IN ('MATERIAL', 'LABOR')) NOT NULL,
  VATRate REAL NOT NULL
);

CREATE TABLE Labor (
  hourlyRate REAL NOT NULL,
  hoursWorked REAL NOT NULL,
  workerProductivity REAL NOT NULL
) INHERITS (Component);

CREATE TABLE Material (
  unitCost REAL NOT NULL,
  quantity REAL NOT NULL,
  transportCost REAL NOT NULL,
  qualityCoefficient REAL NOT NULL
) INHERITS (Component);

CREATE TABLE Client (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    isProfessional BOOLEAN
);

CREATE TABLE Quotation (
  id SERIAL PRIMARY KEY,
  estimatedAmount REAL NOT NULL,
  issueDate DATE NOT NULL,
  validityDate DATE NOT NULL,
  VAT REAL NOT NULL,
  accepted BOOLEAN NOT NULL,
  projectId INTEGER,
  FOREIGN KEY (projectId) REFERENCES Project(id)
);
