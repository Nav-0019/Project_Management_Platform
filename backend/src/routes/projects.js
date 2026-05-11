const express = require('express');
const router = express.Router();
const Project = require('../models/Project');

// Create Project/Team
router.post('/', async (req, res) => {
    try {
        const project = new Project(req.body);
        await project.save();
        res.status(201).json(project);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});

// Get all projects (with filters)
router.get('/', async (req, res) => {
    try {
        const { role, userId } = req.query;
        let query = {};
        if (role === 'student') query = { $or: [{ leader: userId }, { members: userId }] };
        if (role === 'faculty') query = { mentor: userId };
        
        const projects = await Project.find(query).populate('leader members mentor');
        res.json(projects);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

module.exports = router;
