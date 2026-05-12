const express = require('express');
const router = express.Router();
const Project = require('../models/Project');

const { Op } = require('sequelize');

// Create Project/Team
router.post('/', async (req, res) => {
    try {
        const project = await Project.create(req.body);
        res.status(201).json(project);
    } catch (err) {
        res.status(400).json({ error: err.message });
    }
});

// Get all projects (with filters)
router.get('/', async (req, res) => {
    try {
        const { role, userId } = req.query;
        let where = {};
        
        if (role === 'student' && userId) {
            where[Op.or] = [
                { leaderId: userId }
            ];
            // In a real app we'd query the JSON members array, but for temporary SQLite storage 
            // we will fetch all and filter in JS if they are a member.
        }
        if (role === 'faculty' && userId) {
            where.mentorId = userId;
        }
        
        let projects = await Project.findAll({
            where,
            include: ['leader', 'mentor']
        });

        // Manual filter for members since JSON querying in SQLite is tricky via Sequelize where clause
        if (role === 'student' && userId) {
            projects = projects.filter(p => {
                if (p.leaderId === userId) return true;
                if (p.members && Array.isArray(p.members)) {
                    return p.members.some(m => m === userId || m.id === userId);
                }
                return false;
            });
        }

        res.json(projects);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
});

module.exports = router;
